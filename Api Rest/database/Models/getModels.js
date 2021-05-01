const { User } = require('./User')
const { Document } = require('./Document')

User.hasMany(Document, {
    sourceKey: 'nick',
    foreignKey: 'userNick',
    onDelete: 'CASCADE',
    onUpdate: 'CASCADE'
})

Document.belongsTo(User, {
    targetKey: 'nick',
    foreignKey: 'userNick'
})

User.sync()
Document.sync()

exports.User = User
exports.Document = Document