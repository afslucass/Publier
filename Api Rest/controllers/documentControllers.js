
const { writeFileAndReturnId, readFileById, deleteFileById, readFileByIdSync } = require('../utils/imageIO')
const { User, Document } = require('../database/Models/getModels')
const { convertToBase64ByReqFile } = require('../utils/base64Converter')
const { Op } = require('sequelize')

async function getDocumentByNick(req, res, next) {
    let documents
    let image = ''
    let resJson = []

    try {
        documents = await Document.findAll({ where: { userNick: req.params.nick } })
    } catch(err) {
        res.status(400).json({ message: 'Nick Not Defined' })
        return
    }

    if (documents[0] == undefined) { res.status(400).json({ message: 'No Documents' }) }
    else {
        documents.forEach(async (element) => {  
            
            let elementResponse = {}
        
            if (element.imageId != 0) { image = await readFileById(element.imageId) }

            elementResponse = {
                id: element.id,
                title: element.title,
                message: element.message, 
                image: image,
                userNick: element.userNick,
                updatedAt: element.updatedAt,
                createdAt: element.createdAt
            }

            resJson.push(elementResponse)

            if(documents.length == resJson.length) { res.status(200).json(resJson) }
        })
    }
    
}

async function getDocumentById(req, res, next) {
    const document = await Document.findByPk(req.params.id)
    const file = await readFileById(document.imageId)
    response = {
        title: document.title,
        message: document.message,
        image: file,
        createdAt: document.createdAt,
        updatedAt: document.updatedAt,
        id: document.id,
        userNick: document.userNick
    }
    res.json(response).status(200)
}

async function getDocuments(req, res, next) {
    const document = await Document.findAll()
    let resJson = []

    document.forEach(async (element) => {  
            
        let elementResponse = {}

        elementResponse = {
            id: element.id,
            title: element.title,
            userNick: element.userNick,
        }

        resJson.push(elementResponse)

        if(document.length == resJson.length) { res.status(200).json(resJson) }
    })
}

async function getDocumentOrderByDate(req, res, next) {
    
    let documents
    let response = []

    if(req.params.date == '0') {
        
        documents = await Document.findAll({
            order: [
                ['updatedAt', 'DESC']
            ],
            limit: 5
        })

        documents.forEach((doc) => {
            response.push({
                id: doc.id,
                title: doc.title,
                message: doc.message,
                image: readFileByIdSync(doc.imageId),
                createdAt: doc.createdAt,
                updatedAt: doc.updatedAt,
                userNick: doc.userNick
            })
        })
        res.json(response).status(200)
    } else {
        let date
        date = new Date(req.params.date)
        
        try {
            documents = await Document.findAll({
                where: {
                    createdAt: {
                        [Op.lt]: date
                    }
                },
                order: [
                    ['updatedAt', 'DESC']
                ],
                limit: 5
            })
        } catch(err) {
            res.json({ message: 'Format Date ERROR' }).status(400)
            return
        }

        documents.forEach((doc) => {
            response.push({
                id: doc.id,
                title: doc.title,
                message: doc.message,
                image: readFileByIdSync(doc.imageId),
                createdAt: doc.createdAt,
                updatedAt: doc.updatedAt,
                userNick: doc.userNick
            })
        })
        res.json(response).status(200)
    }
    
}

async function postDocument(req, res, next) {
    let user
    let imageId = 0

    try {
        user = await User.findAll({ where: { nick: req.body.userNick } })
    } catch(err) {
        res.status(500).json({ message: 'Db Server Error' })
        return
    }

    if( user[0] == undefined ) {
        res.status(400).json({ message: 'User Not Defined' })    
    } else {

        if(req.file != undefined) { 
            imageId = req.file.filename
            convertToBase64ByReqFile(req.file)
        }

        await user[0].createDocument({
            title: req.body.title,
            message: req.body.message,
            imageId: imageId
        })
        res.status(200).json({})
    }
}

function putDocument(req, res, next) {
    res.json({massage: 'Serviço nao implementado'})
}

function deleteDocumentByNick(req, res, next) {
    res.json({massage: 'Serviço nao implementado'})
}

const documentControllers = {
    getDocumentByNick,
    getDocumentById,
    putDocument,
    postDocument,
    deleteDocumentByNick,
    getDocumentOrderByDate,
    getDocuments
}

exports.documentControllers = documentControllers