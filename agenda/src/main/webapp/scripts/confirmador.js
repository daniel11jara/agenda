/**
 * 
 */

 function confirmar(idcon){
	 
	 let resposta = confirm("Confirma  e exclusao deste contato ?")
	 if (resposta === true){
		 //passo 2
		 //fazendo um redirecionamento
		 window.location.href = "delete?idcon=" + idcon
		 //a palavra delete esta se referindo ao local
		 //?idcon significa que alem de redirecionar estou encaminhando um parametro de nome idcon
	 }
	 
 }