
const { User, Document } = require('../database/Models/getModels')
const { genHash, compareHash } = require('../utils/hashBundler')
const { writeFileAndReturnId, readFileById, deleteFileById } = require('../utils/imageIO')
const { convertToBase64ByReqFile } = require('../utils/base64Converter')

async function getUserByNick(req, res, next) {
    let user
    let image = ''
    let resJson
    try {
        user = await User.findAll({ where: { nick: req.params.nick }, include: Document })
    } catch(err) {
        res.status(400).json({ message: 'User Not Defined' })
        return
    }
    if (user[0] == undefined) { res.status(400).json({ message: 'User Not Defined' }) }
    else {
        if(user[0].imageId != 0) { image = await readFileById(user[0].imageId) }
        resJson = {
            id: user[0].id,
            nick: user[0].nick,
            password: user[0].password,
            date: user[0].date,
            image: image,
            documents: user[0].documents
        }

        res.json(resJson).status(200)
    }

}

async function getUserByNickAndPassword(req, res, next) {
    let user
    let image = ''
    let resJson

    try {
        user = await User.findAll({ where: { nick: req.body.nick } })
    } catch(err) {
        res.status(400).json(err)
        return
    }

    if(typeof user[0] === "undefined") {
        res.status(400).json({message: 'User Not Defined'})
        return
    }

    if (compareHash(req.body.password, user[0].password) == false) {
        res.status(400).json({message:'Password is Incorrect'})
        return
    }

    else {
        if(user[0].imageId != 0) { image = await readFileById(user[0].imageId) }
        resJson = {
            id: user[0].id,
            nick: user[0].nick,
            password: user[0].password,
            date: user[0].date,
            image: image,
        }
        res.json(resJson).status(200)
    }
}

async function postUser(req, res, next) {
    let user
    let imageId = 0
    let image = ''
    try {
        if(req.file != undefined) { 
            imageId = req.file.filename
            convertToBase64ByReqFile(req.file)

            image = await readFileById(imageId)
        }

        user = await User.create({
            nick: req.body.nick,
            password: req.body.password,
            date: req.body.date,
            imageId: imageId,
        })

        res.status(200).json({
            nick: req.body.nick,
            password: user.password,
            date: user.date,
            imageId: imageId,
            updatedAt: user.updatedAt,
            createdAt: user.createdAt,
            image: image,
        })
    } catch(err) {
        if(err.name == 'SequelizeUniqueConstraintError') {
            res.status(400).json({ message: 'The Name must be Unique' })
        } else {
            res.status(400).json({ message: 'Error Not Defined' })
        }
        await deleteFileById(imageId)
    }
}

function putUser(req, res, next) {
    res.json({ message: 'Serviço nao implementado' })
}

function deleteUserByNick(req, res, next) {
    res.json({ message: 'Serviço nao implementado' })
}

const userControllers = {
    getUserByNick,
    putUser,
    postUser,
    deleteUserByNick,
    getUserByNickAndPassword
}

exports.userControllers = userControllers