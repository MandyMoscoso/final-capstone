const registerForm = document.getElementById('register-form')
const registerUsername = document.getElementById('username')
const registerPassword = document.getElementById('password')
const registerFirstname = document.getElementById('firstname')
const registerLastname = document.getElementById('lastname')
const registerPhonenumber = document.getElementById('phonenumber')
const alertPlaceholder = document.getElementById('liveAlertPlaceholder')
const csrfToken = document.getElementById("csrf")
const headers = {
'Content-Type': 'application/json',
"X-CSRF-Token": csrfToken.value
}
const baseUrl = 'http://localhost:8080'

const handleSubmit = async (e) => {
    e.preventDefault()

    let bodyObj = {
    username: registerUsername.value,
    password: registerPassword.value,
    firstname: registerFirstname.value,
    lastname: registerLastname.value,
    phonenumber: registerPhonenumber.value
    }
    console.log(bodyObj)
    const response = await fetch(`${baseUrl}/register`, {
        method: "POST",
        body: JSON.stringify(bodyObj),
        headers: headers
    })
        .catch(err => console.error(err.message))
    if (response.status === 200){
        const responseArr = await response.json()
        alert(responseArr[responseArr.length -2], responseArr[responseArr.length -1]);
        if(responseArr[responseArr.length -1]==="success"){
            registerFirstname.value = "";
            registerLastname.value = "";
            registerUsername.value ="";
            registerPhonenumber.value = "";
            registerPassword.value="";
        }
    }

}
const alert = (message, type) => {
    alertPlaceholder.innerHTML="";
    const wrapper = document.createElement('div')
    wrapper.innerHTML =
        `<div class="alert alert-${type} alert-dismissible" role="alert">
        <div>${message}</div>
          <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>`

    alertPlaceholder.appendChild(wrapper)
}
registerForm.addEventListener("submit", handleSubmit)
