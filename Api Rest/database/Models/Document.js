const { DataTypes } = require('sequelize')
const { sequelize } = require('../getConnection')

const Document = sequelize.define('document', {
    title: {
        type: DataTypes.STRING(64),
        allowNull: false,
    },
    message: {
        type: DataTypes.STRING(5000),
        allowNull: false
    },
    imageId: {
        type: DataTypes.STRING(64),
        allowNull: true
    }
}, {})

exports.Document = Document
