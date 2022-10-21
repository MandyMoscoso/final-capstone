
const baseUrl = "http://localhost:8080";
const csrfToken = document.getElementById("csrf")
const userId = document.getElementById("userid").value
const role = document.getElementById("role")
const userInfoForm = document.getElementById("user-info-form")
const borrowerInfoForm = document.getElementById("borrower-info-form")

const firstName = document.getElementById("firstname");
const lastName = document.getElementById("lastname");
const email = document.getElementById("username");
const password = document.getElementById("password");
const phone = document.getElementById("phone-number");
const address = document.getElementById("address");
const creditScore = document.getElementById("credit-score");
const loanAmount = document.getElementById("loan-amount");
const loanPurpose = document.getElementById("loan-purpose");
const loanTerm = document.getElementById("loan-term");
const loanType = document.getElementById("loan-type");
const occupancy = document.getElementById("occupancy");
const propertyType = document.getElementById("property-type");
const propertyValue = document.getElementById("property-value");
const saveUser = document.getElementById("saveBtn");


const editUser= (item) => {
    const editForm = document.createElement("form");
    editForm.innerHTML= `<input id="edit-${item}" type = "text" placeholder = "Enter new value"></input><input type="button" onclick="submitEdit('${item}')" value="Submit" id='userID'> </input>`;
    document.querySelector(`#${item}`).innerHTML=""
    document.querySelector(`#${item}`).appendChild(editForm);
    return ;
}

const headers = {
'Content-Type': 'application/json',
"X-CSRF-Token": csrfToken.value
}

//const submitEdit = (item) =>{
//let editItem = document.getElementById(item);
//editItem.innerHTML=document.getElementById("edit-" + item).value;
//};
//
//const  getUserInfo = async() =>{
//    const response = await fetch(`${baseUrl}/getuser/${userId}`, {
//        method: "GET",
//        headers: {
//                 'Content-Type': 'application/json',
//                 }
//    })
//            .then(response => response.json())
//             .then(data =>{
//                 populateUser(data)
//              })
//             .catch(err => console.error(err))
//}
//start the page with loading user info. should be commmented out when it is admin
//getUserInfo();

//const populateUser = (obj) =>{
//    firstName.innerHTML=obj.firstname;
//    lastName.innerHTML=obj.lastname;
//    email.innerHTML=obj.username;
//    phone.innerHTML=obj.phonenumber;
//    address.innerHTML=obj.borrowerDto.address;
//    creditScore.innerHTML=obj.borrowerDto.creditScore;
//    loanAmount.innerHTML=obj.borrowerDto.loanAmount
//    loanPurpose.innerHTML=obj.borrowerDto.loanPurpose
//    loanTerm.innerHTML=obj.borrowerDto.loanTerm
//    loanType.innerHTML=obj.borrowerDto.loanType
//    occupancy.innerHTML=obj.borrowerDto.occupancyType
//    propertyType.innerHTML=obj.borrowerDto.propertyType
//    propertyValue.innerHTML=obj.borrowerDto.propertyValue
//}



const handleSubmit = async () => {
    let bodyObj = {
        firstname: firstName.value,
        lastname: lastName.value,
        password: password.value,
        username: email.value,
        phonenumber: phone.value
    }
    if(role.value=="ROLE_USER"){
    bodyObj.borrowerDto = {
                                  address: address.value,
                                  creditScore: creditScore.value,
                                  loanAmount: loanAmount.value,
                                  loanPurpose: loanPurpose.value,
                                  loanTerm: loanTerm.value,
                                  loanType: loanType.value,
                                  occupancyType: occupancy.value,
                                  propertyType: propertyType.value,
                                  propertyValue: propertyValue.value,
                                  }
    }
    bodyObj = JSON.stringify(bodyObj)
    console.log(bodyObj)
    await fetch(`${baseUrl}/admin/createuser/${role.value}`, {
                  method: "POST",
                  body: bodyObj,
                  headers: headers
              })
                  .catch(err => console.error(err))

}

//to show form when role admin or staff is chosen

const showStaffsForm = () =>{
    userInfoForm.classList.remove("d-none")
    if(role.value==="ROLE_USER"  ){
    borrowerInfoForm.classList.remove("d-none")
    }
}

