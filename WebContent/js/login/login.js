$( document ).ready(function() {
	// Get the modal
	var modal1 = document.getElementById('id01');
	var modal2 = document.getElementById('myModal');
	// When the user clicks anywhere outside of the modal, close it
	window.onclick = function(event) {
	    if (event.target == modal1) {
	        modal.style.display = "none";
	    }/*else{
	    	modal.style.display = "none";
	    }*/
	}
});

