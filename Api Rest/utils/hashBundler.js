const bcrypt = require('bcrypt')

function genHash(plainText, salt) {
    const encryp = bcrypt.hashSync(plainText, salt)
    return encryp
}

function compareHash(pass, passHashed) {
    const encryp = bcrypt.compareSync(pass, passHashed)
    return encryp
}

exports.genHash = genHash
exports.compareHash = compareHash