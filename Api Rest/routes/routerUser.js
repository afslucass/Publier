const express = require('express')
const { userControllers } = require('../controllers/userControllers')
const { uploads } = require('../multerConfig')

const router = express.Router()

router.get('/user/get/:nick', async (req, res, next) => { await userControllers.getUserByNick(req, res, next) })
router.post('/user/login', uploads.any(), async (req, res, next) => { await userControllers.getUserByNickAndPassword(req, res, next) })
router.post('/user/post', uploads.single('image'), async (req, res, next) => { await userControllers.postUser(req, res, next) })
router.put('/user/put', async (req, res, next) => { await userControllers.putUser(req, res, next) })
router.delete('/user/delete/:nick', async (req, res, next) => { await userControllers.deleteUserByNick(req, res, next) })

exports.routerUser = router