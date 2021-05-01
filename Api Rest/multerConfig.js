const multer = require('multer')

var uploads = multer({
    dest: 'uploads/'
})

exports.uploads = uploads