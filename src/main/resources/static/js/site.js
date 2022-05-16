window.onload=function(){
    document.getElementById('addForm').addEventListener('submit', addUserButton);
    document.getElementById('updateForm').addEventListener('submit', updateUserButton);
}

function dataEditUser(userId){
    let arrayOfIds = ["id", "first_name", "last_name", "age", "email"];
    for (let i = 0; i < arrayOfIds.length; i++){
        addToEditData(userId + arrayOfIds[i], arrayOfIds[i]);
    }
}

function addToEditData(readTextFromId, inputId){
    let valueName = $("#" + readTextFromId).text();
    document.querySelector("#updateForm input[name = '"+ inputId +"']").setAttribute("value", valueName);
}

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
        } else if(datas['roles'][0]['role'] == "ROLE_ADMIN"){
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
        $("#addForm input[name = 'first_name']").val("First Name");
        $("#addForm input[name = 'last_name']").val("Last Name");
        $("#addForm input[name = 'age']").val("1");
        $("#addForm input[name = 'password']").val("");
        $("#addForm select[name = 'roles']").val("");
        $("#addForm input[name = 'email']").val("Email");
        alert("Пользователь добавлен");

        //добавить переход на таюлицу
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

function updateUserButton(event){
    event.preventDefault();
    let userToAdd = {};
    userToAdd["id"] = $("#updateForm input[name = 'id']").val();
    userToAdd["firstName"] = $("#updateForm input[name = 'first_name']").val();
    userToAdd["lastName"] = $("#updateForm input[name = 'last_name']").val();
    userToAdd["age"] = $("#updateForm input[name = 'age']").val();
    userToAdd["password"] = $("#updateForm input[name = 'password']").val();
    userToAdd["roles"] = $("#updateForm select[name = 'roles']").val();
    userToAdd["email"] = $("#updateForm input[name = 'email']").val();
    addUser(urlData, userToAdd, 'PUT').then((response) => {
        return response.json();
    }).then((datas)=>{
        let roleString;
        let id = datas[id];
        if(datas['roles'].length == 2){
            roleString = "ADMIN, USER";
        } else if(datas['roles'][0]['role'] == "ROLE_ADMIN"){
            roleString = "ADMIN";
        } else {
            roleString = "USER";
        }
        //update table
        $("tr#" + id);
        console.log(datas);
        alert("Пользователь обновлен");

        //добавить переход на таюлицу
    });
}







