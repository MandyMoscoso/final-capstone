
const baseUrl = "http://localhost:8080";
const csrfToken = document.getElementById("csrf")
const userId = document.getElementById("userid").textContent
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

const submitEdit = (item) =>{

console.log(item)
let editItem = document.getElementById(item);
editItem.innerHTML=document.getElementById("edit-" + item).value;
};

const  getUserInfo = async() =>{
    const response = await fetch(`${baseUrl}/getuser/${userId}`, {
        method: "GET",
        headers: {
                 'Content-Type': 'application/json',
                 }
    })
            .then(response => response.json())
             .then(data =>{
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
    loanPurpose.innerHTML=obj.borrowerDto.loanPurpose
    loanTerm.innerHTML=obj.borrowerDto.loanTerm
    loanType.innerHTML=obj.borrowerDto.loanType
    occupancy.innerHTML=obj.borrowerDto.occupancyType
    propertyType.innerHTML=obj.borrowerDto.propertyType
    propertyValue.innerHTML=obj.borrowerDto.propertyValue
}

getUserInfo();

const handleSubmit = async () => {
    let bodyObj = {
        id: userId,
        firstname: firstName.textContent,
        lastname: lastName.textContent,
        password: password.textContent,
        username: email.textContent,
        phonenumber: phone.textContent,
        borrowerDto: {
        address: address.textContent,
        creditScore: creditScore.textContent,
        loanAmount: loanAmount.textContent,
        loanPurpose: loanPurpose.textContent,
        loanTerm: loanTerm.textContent,
        loanType: loanType.textContent,
        occupancyType: occupancy.textContent,
        propertyType: propertyType.textContent,
        propertyValue: propertyValue.textContent,
        }
    }
    bodyObj = JSON.stringify(bodyObj)
    console.log(bodyObj)
    await fetch(`${baseUrl}/edituser/${userId}`, {
                  method: "PUT",
                  body: bodyObj,
                  headers: headers
              })
                  .catch(err => console.error(err))
    password.innerHTML=''

}

