function dataEditUser(userId){
    var arrayOfIds = ["id", "first_name", "last_name", "age", "email"];
    for (let i = 0; i < arrayOfIds.length; i++){
        addToEditData(userId + arrayOfIds[i], arrayOfIds[i]);
    }
    $("#myModal").fadeIn();
}
function addToEditData(readTextFromId, inputId){
    let valueName = $("#" + readTextFromId).text();
    document.querySelector("#updateForm input[name = '"+ inputId +"']").setAttribute("value", valueName);
}

// function fadeModal(){
//     $("#myModal").fadeOut();
// }