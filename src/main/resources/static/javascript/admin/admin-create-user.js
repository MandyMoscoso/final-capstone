
const baseUrl = "http://localhost:8080";
const csrfToken = document.getElementById("csrf")
const userInfoForm = document.getElementById("user-info-form")
const borrowerInfoForm = document.getElementById("borrower-info-form")


const firstName = document.getElementById("firstname");
const lastName = document.getElementById("lastname");
const email = document.getElementById("username");
const password = document.getElementById("password");
const phone = document.getElementById("phone-number");
const role = document.getElementById("role");
const address = document.getElementById("address");
const creditScore = document.getElementById("credit-score");
const loanAmount = document.getElementById("loan-amount");
const loanPurpose = document.getElementById("loan-purpose");
const loanTerm = document.getElementById("loan-term");
const loanType = document.getElementById("loan-type");
const occupancy = document.getElementById("occupancy");
const propertyType = document.getElementById("property-type");
const propertyValue = document.getElementById("property-value");




const headers = {
'Content-Type': 'application/json',
"X-CSRF-Token": csrfToken.value
}
const addUser = async () => {
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
        .then(window.location.reload())

}

const showStaffsForm = () =>{
    userInfoForm.classList.remove("d-none")
    if(role.value==="ROLE_USER"  ){
    borrowerInfoForm.classList.remove("d-none")
    } else{
        borrowerInfoForm.classList.add("d-none")
    }
}
