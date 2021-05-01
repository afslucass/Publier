const { sequelize } = require('../getConnection')
const { genHash, compareHash } = require('../../utils/hashBundler')

const { Document } = require('./Document')
const { DataTypes } = require("sequelize")

const User = sequelize.define('user', {
    nick: {
        type: DataTypes.STRING(64),
        unique: true,
        allowNull: false,
    },
    password: {
        type: DataTypes.STRING(64),
        allowNull: false,
        set(val) {
            this.setDataValue('password', genHash(val, 5))
        }
    },
    date: {
        type: DataTypes.DATEONLY,
        allowNull: false
    },
    imageId: {
        type: DataTypes.STRING(64),
        allowNull: true
    }
}, {})

exports.User = User