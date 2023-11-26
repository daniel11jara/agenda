/**
 * 
 */
function validar(){
	//variavel nome pegando do formulario o campo nome o seu valor
	//pegando do arquivo novo.html
	let nome = frmContato.nome.value 
	let fone = frmContato.fone.value
	
	//Passo 3 do diagrama
	//verificando se os campos estao vazios
	if(nome === ""){
		alert('Preencha o campo nome')
		frmContato.nome.focus() //posicionando o cursor na campo nome
		return false  //se o usuario nao preencher o formulario nao sera enviado
	} else if (fone === ""){
		alert('Preencha o campo Fone')
		frmContato.nome.focus()
		return false  
	} else {
		document.forms["frmContato"].submit()
	}
}