


 function alert2(id) {
  	idd="#"+id;
  		   // Alert combination
    $(idd).on('click', function() {
        swal({
            title: "Etes-vous sur?",
            text: "Vous ne pourrez pas récupérer les informations!",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#EF5350",
            confirmButtonText: "Oui, supprimé!",
            cancelButtonText: "Non, annulé!",
            closeOnConfirm: false,
            closeOnCancel: false
        },
        function(isConfirm){
            if (isConfirm) {
                swal({
                    title: "Supprimé!",
                    text: "Vos informations ont été supprimé.",
                    confirmButtonColor: "#66BB6A",
                    type: "success"
     
                });

            }
            else {
                swal({
                    title: "Annulé",
                    text: "Vos informations sont en sécurité 🙂",
                    confirmButtonColor: "#2196F3",
                    type: "error"
                });
        
            }
        });
    });




  	}


  	function addTask(){
  		
  		name=document.getElementById("nom").value;
  		assigner=document.getElementById("assigner").value;
  		desc=document.getElementById("desc").value;
  		heure=document.getElementById("heure").value;
  		//couleur=document.getElementById("couleur").value;



			 res ="						<div class=\"col-md-4\">" + 
			"											" + 
			"							<!-- Progress bar -->" + 
			"							<div class=\"panel panel-flat\">" + 
			"								<div class=\"panel-heading\">\n" + 
			"									<h6 class=\"panel-title\">"+name+"</h6>" + 
			"								</div>" + 
			"								" + 
			"								" + 
			"" + 
			"								<div class=\"panel-footer\">" + 
			"									<div class=\"heading-elements\">" + 
			"									<span class=\"heading-text\"><span class=\"label label-default label-rounded\">"+assigner+"</span></span>" + 
			"										<span class=\"heading-text\"><i class=\"icon-spinner2 spinner\"></i> "+heure+" Heures</span>" + 
			"									<div class=\"progress progress-rounded\">" + 
			"										<div class=\"progress-bar progress-bar-primary\" style=\"width: 0%\">" + 
			"											<span>0% Complete</span>" + 
			"										</div>" + 
			"									</div>" + 
			"									</div>" + 
			"								</div>" + 
			"							</div>" + 
			"							<!-- /progress bar -->" + 
			"" + 
			"											" + 
			"						</div>";
			document.getElementById("backlog1").innerHTML+=res;

  	}


function test(){
	cc=document.getElementById("cc").value;
	res="							<!-- Progress bar -->\n" + 
			"							<div class=\"panel panel-flat\">\n" + 
			"								<div class=\"panel-heading\" style=\"background-color:"+cc+"\">\n" + 
			"									<h6 class=\"panel-title\">Couche Metier</h6>\n" + 
			"								</div>\n" + 
			"								\n" + 
			"								\n" + 
			"\n" + 
			"								<div class=\"panel-footer\">\n" + 
			"									<div class=\"heading-elements\">\n" + 
			"									<span class=\"heading-text\"><span class=\"label label-default label-rounded\">Mohsine Bahhou</span></span>\n" + 
			"										<span class=\"heading-text\"><i class=\"icon-spinner2 spinner\"></i> 6 Heures</span>\n" + 
			"									<div class=\"progress progress-rounded\">\n" + 
			"										<div class=\"progress-bar progress-bar-primary\" style=\"width: 76%\">\n" + 
			"											<span>76% Complete</span>\n" + 
			"										</div>\n" + 
			"									</div>\n" + 
			"									</div>\n" + 
			"								</div>\n" + 
			"							</div>\n" + 
			"							<!-- /progress bar -->";
	document.getElementById("test").innerHTML=res;
	alert(cc);
}



function getUser(u){

    d= "formAjax:uuu"  ;
    //alert(d);
	document.getElementById(d).value=u;
}
function ll(r){
	//alert(r);
	if(r != "rien") {
		$(r).modal('show');
	}

}

function getIdBacklog(){
	//alert("xvx");
	//document.getElementById("iddback").value=bb;
}


function MessageOn(){
	document.getElementById("messageon").style.display = "block";
	document.getElementById("messageon").focus();
}
function MessageOff(){
	document.getElementById("messageon").style.display = "none";
	document.getElementById("lastmessage").focus();
}

function initchat(){
	document.getElementById("lastmessage").focus();
}

function changerprojet(p){
    d= "formAjax:ppcurrent"  ;
    alert(p);
	document.getElementById(d).value=p;
}


