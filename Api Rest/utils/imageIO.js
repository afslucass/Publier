const fs = require('mz/fs')
const fsSync = require('fs')
const path = require('path')

async function readFileById(id) {
    let data = await fs.readFile(path.join(__dirname, '..', '/uploads/') + id)
    return data.toString()
}

function readFileByIdSync(id) {
    let data = fsSync.readFileSync(path.join(__dirname, '..', '/uploads/') + id, { flag: 'r' })
    return data.toString()
}

async function deleteFileById(id) {
    await fs.rm(path.join(__dirname, '..', '/uploads/') + id, (err) => console.log(err))
}

exports.readFileById = readFileById
exports.deleteFileById = deleteFileById
exports.readFileByIdSync = readFileByIdSync