const express = require('express')
const bodyParser = require('body-parser')
const cors = require('cors')

const { routerUser } = require('./routes/routerUser')
const { routerDocument } = require('./routes/routerDocument')

const app = express()

const corsOptions = {
  origin: 'http://localhost:8080'
}
app.use(cors(corsOptions))

app.use(routerDocument)
app.use(routerUser)

app.listen(3000, () => console.log('O Servidor Esta Rodando'))