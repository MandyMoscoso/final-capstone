
const baseUrl = "http://localhost:8080";
const csrfToken = document.getElementById("csrf")
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
    const type =   document.querySelector(`#${item}`).getAttribute("type");
    editForm.innerHTML= `<input id="edit-${item}" type=${type} placeholder = "Enter new value"></input><input type="button" onclick="submitEdit('${item}')" value="Submit" id='userID'> </input>`;
    document.querySelector(`#${item}`).innerHTML=""
    document.querySelector(`#${item}`).appendChild(editForm);
    return ;
}

const headers = {
'Content-Type': 'application/json',
"X-CSRF-Token": csrfToken.value
}

const submitEdit = (item) =>{

console.log(item)
let editItem = document.getElementById(item);
editItem.innerHTML=document.getElementById("edit-" + item).value;
};

const  getUserInfo = async() =>{
    const response = await fetch(`${baseUrl}/getuser`, {
        method: "GET",
        headers: {
                 'Content-Type': 'application/json',
                 }
    })
            .then(response => response.json())
             .then(data =>{
             console.log(data)
                 populateUser(data)
              })
             .catch(err => console.error(err))
}

const populateUser = (obj) =>{
    firstName.innerHTML=obj.firstname;
    lastName.innerHTML=obj.lastname;
    email.innerHTML=obj.username;
    phone.innerHTML=obj.phonenumber;
    address.innerHTML=obj.borrowerDto.address;
    creditScore.innerHTML=obj.borrowerDto.creditScore;
    loanAmount.innerHTML=obj.borrowerDto.loanAmount
    // propertyType.innerHTML=obj.borrowerDto.propertyType
    propertyValue.innerHTML=obj.borrowerDto.propertyValue

    const loanPurposeSelection = document.getElementById("loan-purpose");
    (function() {
        for(let i =0; i< loanPurposeSelection.length; i++){
            if( loanPurposeSelection[i].value=== obj.borrowerDto.loanPurpose){
                loanPurposeSelection[i].selected ="selected";
            }
        }
    }());
    const loanTerm = document.getElementById("loan-term");
    (function() {
        for(let i =0; i< loanTerm.length; i++){
            if( loanTerm[i].value=== obj.borrowerDto.loanTerm){
                loanTerm[i].selected ="selected";
            }
        }
    }());

    const loanType = document.getElementById("loan-type");
    (function() {
        for(let i =0; i< loanType.length; i++){
            if( loanType[i].value=== obj.borrowerDto.loanType){
                loanType[i].selected ="selected";
            }
        }
    }());
    const occupancy = document.getElementById("occupancy");
    (function() {
        for(let i =0; i< occupancy.length; i++){
            if( occupancy[i].value=== obj.borrowerDto.occupancyType){
                occupancy[i].selected ="selected";
            }
        }
    }());

    const propertyType = document.getElementById("property-type");
    (function() {
        for(let i =0; i< propertyType.length; i++){
            if( propertyType[i].value=== obj.borrowerDto.propertyType){
                propertyType[i].selected ="selected";
            }
        }
    }());
}

getUserInfo();

const handleSubmit = async () => {
    let bodyObj = {
        firstname: firstName.textContent,
        lastname: lastName.textContent,
        password: password.textContent,
        username: email.textContent,
        phonenumber: phone.textContent,
        borrowerDto: {
        address: address.textContent,
        creditScore: creditScore.textContent,
        loanAmount: loanAmount.textContent,
        loanPurpose: loanPurpose.value,
        loanTerm: loanTerm.value,
        loanType: loanType.value,
        occupancyType: occupancy.value,
        propertyType: propertyType.value,
        propertyValue: propertyValue.textContent,
        }
    }
    bodyObj = JSON.stringify(bodyObj)
    console.log(bodyObj)
    await fetch(`${baseUrl}/edituser`, {
                  method: "PUT",
                  body: bodyObj,
                  headers: headers
              })
                  .catch(err => console.error(err))
    password.innerHTML=''

}

