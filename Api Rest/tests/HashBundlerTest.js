const { compare } = require('bcrypt')
const { genHash, compareHash } = require('../utils/hashBundler')

plainText = 'Lucas123'

async function asd() {
    let passHashed = await genHash(plainText, 5)
    console.log(passHashed)
    let isTheSame = await compareHash(plainText, passHashed)
    console.log(isTheSame)
}

asd()
