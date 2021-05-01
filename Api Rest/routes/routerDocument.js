const express = require('express')
const { documentControllers } = require('../controllers/documentControllers')
const { uploads } = require('../multerConfig')

const router = express.Router()

router.get('/document/get/nick/:nick', async (req, res, next) => { await documentControllers.getDocumentByNick(req, res, next) })
router.get('/document/get', async (req, res, next) => { await documentControllers.getDocuments(req, res, next) })
router.get('/document/get/:id', async (req, res, next) => { await documentControllers.getDocumentById(req, res, next) })
router.get('/document/get/date/:date', async (req, res, next) => { await documentControllers.getDocumentOrderByDate(req, res, next) })
router.post('/document/post', uploads.single('image'), async (req, res, next) => { await documentControllers.postDocument(req, res, next) })
router.put('/document/put', async (req, res, next) => { await documentControllers.putDocument(req, res, next) })
router.delete('/document/delete/:nick', async (req, res, next) => { await documentControllers.deleteDocumentByNick(req, res, next) })

exports.routerDocument = router