
const fs = require('fs')
const path = require('path')

function convertToBase64ByReqFile(image) {
    let data = fs.readFileSync(path.join(__dirname, '..', '/uploads/') + image.filename)

    fs.rmSync(path.join(__dirname, '..', '/uploads/') + image.filename)

    fs.writeFileSync(path.join(__dirname, '..', '/uploads/') + image.filename, data.toString('base64'))
}

exports.convertToBase64ByReqFile = convertToBase64ByReqFile