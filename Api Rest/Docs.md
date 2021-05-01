# User Routes Documentation

## GET /user/get/:nick: getUserByNick

### Params
    
    nick:

### Response

    200: 
        id
        nick
        password
        date
        image: String
        documents: [
            {
                title
                message
                imageId
            },
            ...
        ]
    400: 
        message: 'User not Defined'

## POST /user/login: getUserByNickAndPassword

### Params
    
    nick:
    password:

### Response

    200: 
        id
        nick
        password
        date
        image: String
    400: 
        message: 'User not Defined'
    400:
        message: 'Password is Incorrect'

## POST /user/post: postUser

### Params
    
    nick:
    password:
    image: 
    date: 

### Response

    200: 
        nick
        password
        date
        image: String
    400: 
        message: 'The name must be unique'
    400: 
        message: 'Error not Defined'

# Document Routes Documentation

## GET /document/get: getDocuments

### Response

    200: 
        id
        title
        userNick

## GET /document/get/:nick: getDocumentByNick

### Params
    
    nick:

### Response

    200: 
        id
        title
        message
        image: String
        userNick
        updatedAt
        createdAt
    400: 
        message: 'No Documents'
    400: 
        message: 'Nick Not Defined'

## GET /document/get/:id: getDocumentById

### Params
    
    id:

### Response

    200: 
        id
        title
        message
        image: String
        userNick
        updatedAt
        createdAt

## GET /document/get/:date: getDocumentOrderByDate

### Params
    
    date (image updatedAt):

### Response

    200: 
        id
        title
        message
        image: String
        userNick
        updatedAt
        createdAt
    400:
        message: 'format error'

## POST /document/post: postDocument

### Params
    
    userNick:
    image:
    title:
    message:

### Response

    200: 
        
    400: 
        message: 'User Not Defined'
    500: 
        message: 'Db Server Error'