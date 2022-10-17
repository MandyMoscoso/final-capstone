const registerForm = document.getElementById('register-form')
const registerUsername = document.getElementById('username')
const registerPassword = document.getElementById('password')
const registerFirstname = document.getElementById('firstname')
const registerLastname = document.getElementById('lastname')
const registerPhonenumber = document.getElementById('phonenumber')
const csrfToken = document.getElementById("csrf")
const headers = {
//'Content-Type': 'application/json',
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
    const responseArr = await response.text()
    if(response.status === 200){
    window.location.replace(responseArr[0])
    }
}

registerForm.addEventListener("submit", handleSubmit)
