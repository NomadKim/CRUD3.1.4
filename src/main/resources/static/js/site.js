window.onload=function(){
    document.getElementById('addForm').addEventListener('submit', addUserButton);
}

function dataEditUser(userId){
    let arrayOfIds = ["id", "first_name", "last_name", "age", "email"];
    for (let i = 0; i < arrayOfIds.length; i++){
        addToEditData(userId + arrayOfIds[i], arrayOfIds[i]);
    }
    // $("#myModal").fadeIn();
}
function addToEditData(readTextFromId, inputId){
    let valueName = $("#" + readTextFromId).text();
    document.querySelector("#updateForm input[name = '"+ inputId +"']").setAttribute("value", valueName);
}


// function fadeModal(){
//     $("#myModal").fadeOut();
// }
let urlData = "/admin/users/";
async function addUser(url, data, method){
    return await fetch(url,{
        method: method,
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    });
}

function addUserButton(event){
    event.preventDefault();
    let userToAdd = {};
    userToAdd["firstName"] = $("#addForm input[name = 'first_name']").val();
    userToAdd["lastName"] = $("#addForm input[name = 'last_name']").val();
    userToAdd["age"] = $("#addForm input[name = 'age']").val();
    userToAdd["password"] = $("#addForm input[name = 'password']").val();
    userToAdd["roles"] = $("#addForm select[name = 'roles']").val();
    userToAdd["email"] = $("#addForm input[name = 'email']").val();
    addUser(urlData, userToAdd, 'POST').then((response) => {
        return response.json();
    }).then((datas)=>{
        let roleString;

        if(datas['roles'].length == 2){
            roleString = "ADMIN, USER";
        } else if(datas['roles'][0] == "ROLE_ADMIN"){
            roleString = "ADMIN";
        } else {
            roleString = "USER";
        }
        let addTr = "<tr id = '" + datas['id'] + "'> " +
            "<td id='" + datas['id'] + "'>" + datas['id'] + "</td>"+
            "<td id='" + datas['id'] + "first_name'>" + datas['firstName'] +"</td>"+
            "<td id='" + datas['id'] + "last_name'>" + datas['lastName'] +"</td>"+
            "<td id='" + datas['id'] + "email'>" + datas['email'] +"</td>"+
            "<td id='" + datas['id'] + "roles'>" + roleString +"</td>"+
            "<td id='" + datas['id'] + "age'>" + datas['age'] +"</td>"+
            "<td><button onClick='dataEditUser(this.id)' id='"+ datas['id'] +"'"+
        "class='btn btn-info' data-toggle='modal' data-target='#myModal'> Edit </button> </td>"+
        "<td> <button onclick='deleteUserButton(" + datas['id'] + ")' class='btn btn-danger'>"+
                "Delete </button> </td> </tr>"
        $("tbody").append(addTr);
        console.log(datas);
    });
}

function deleteUserButton(userId){
    let deleteUrl = urlData + userId;
    addUser(deleteUrl, undefined, 'DELETE').then((responce) => {
        if (responce.status == 200){
            $("tr#" + userId).remove();
        }
    });
}







