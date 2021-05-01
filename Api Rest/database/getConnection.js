const { Sequelize } = require('sequelize')

const sequelize = new Sequelize('layouttest', 'root', '----------', {
    dialect: 'mysql',
})

exports.sequelize = sequelize