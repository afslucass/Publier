
window.addEventListener('dragover', function(e) {
  e = e || event
  e.preventDefault()
}, false)
window.addEventListener('drop', function(e) {
  e = e || event
  e.preventDefault()
}, false)

var FontAttributor = Quill.import('attributors/class/font');
FontAttributor.whitelist = [
	'', 'oswald', 'lato'
];

var toolbarOptions = [
  ['bold', 'italic', 'underline', 'strike'],        // toggled buttons

  [{ 'direction': 'rtl' }],                         // text direction

  [{ 'size': ['small', false, 'large', 'huge'] }],  // custom dropdown

  [{ 'color': [] }, { 'background': [] }],          // dropdown with defaults from theme
  [{ 'font': FontAttributor.whitelist }],
  [{ 'align': [] }],
];

Quill.register(FontAttributor, true);

var quill = new Quill('#editor', {
    theme: 'snow',
	placeholder: 'Hello Baby, write your poems here!!',
	formats: {
		image: false
	},
	modules: {
		toolbar: toolbarOptions
	}
});

var form = document.querySelector('form')

form.addEventListener('submit', (event) => {
	event.preventDefault()

	form.message.value = quill.root.innerHTML
	form.title.value = quill.getContents().ops[0].insert.toString().split('\n')[0]

	if(form.message.value.length === 0 || !form.message.value.trim() || form.title.value.length === 0 || !form.title.value.trim() || form.title.value.length > 50) {
			
	} else { form.submit() }
})