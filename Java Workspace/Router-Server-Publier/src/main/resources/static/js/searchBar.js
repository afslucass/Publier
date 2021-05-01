
  let resp = 0

  new autoComplete({
    data: {                    
      src: async () => {
		
		if(resp == 0) {
			let data = await fetch('http://localhost:3000/document/get/')
			let json = await data.json()
			
			resp = json	
		}
		
        return resp
      },
      key: ["title"], //  'userNick', 'id'
      cache: false
    },
    placeHolder: "Pesquise por Posts...",   
    selector: "#search-bar-input",           
    observer: true,                      
    threshold: 3,                        
    debounce: 300,                       
    searchEngine: "strict",              
    resultsList: {                       
        destination: "#search-bar-input",
        position: "afterend",
        element: "div",
        className: 'container-sugestions'
    },
    maxResults: 5,              
    resultItem: {                  
        content: (data, source) => {
            source.innerHTML = data.match + '<div class="sugestions-item-nick">' + data.value.userNick + '</div>'; // Substituir lala pelo nome do autor
        },
        className: 'sugestions-item-container',
        element: "div"
    },
    onSelection: feedback => {     
        window.location.href = "http://localhost:8080/show-post-controller/" + feedback.selection.value.id; 
    }
});