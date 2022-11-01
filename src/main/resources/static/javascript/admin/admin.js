
const baseUrl = "http://localhost:8080";
const csrfToken = document.getElementById("csrf")
const logOutBtn = document.getElementById("log-out")
const pageContent = document.getElementById("home-content")
const alertPlaceholder = document.getElementById('liveAlertPlaceholder')


const role = document.getElementById("role");
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
const allUserTable = document.getElementById("all-user-table");


const headers = {
'Content-Type': 'application/json',
"X-CSRF-Token": csrfToken.value
}

const createUserCard = (obj) =>{
    allUserTable.innerHTML="";
    obj.forEach(obj => {
        let roles = "";
        for (let i of obj.authorities){
            roles += i.authority.replace("ROLE_","") +", "
        };
        roles = roles.slice(0,-2);
        let userCard = document.createElement("tr");
        userCard.classList.add("m-2");
        userCard.innerHTML = `
             <td class="text-center"><input value=${obj.id} class = "form-check-input " type="checkbox" name = "selections" ></td>             
             <td>${obj.firstname}</td>
             <td>${obj.lastname}</td>
             <td>${obj.username}</td>
             <td>${obj.phonenumber}</td>
             <td>${roles}</td>
             <td class="text-center"> <button type="submit" class="btn btn-outline-secondary btn-sm" name="edit" value="true" onclick="editUser(${obj.id},'${roles}')">Edit</button>
</td>
        `
        allUserTable.append(userCard);
    })
}



//GET USER INFO PAGE
const  getAllUserInfo = async() =>{
    const response = await fetch(`${baseUrl}/admin/alluser`, {
        method: "GET",
        headers: {
                 'Content-Type': 'application/json',
                 }
    })
            .then(response => response.json())
             .then(data =>{
                createUserCard(data)
              })
             .catch(err => console.error(err))

}
//start the page with loading user info. should be commmented out when it is admin
getAllUserInfo();

const deleteUsers =async() =>{
    let selections = "";
    for (let i = 0; i <allUserTable.rows.length; i++){
        if(allUserTable.rows.item(i).cells.item(0).firstElementChild.checked ){
             selections+=(allUserTable.rows.item(i).cells.item(0).firstElementChild.value)+"-"
        }
    }
    selections = selections.slice(0, -1);
    const response = await fetch(`${baseUrl}/admin/delete/${selections}`, {
        method: "DELETE",
        headers: headers
    })
        .catch(err => console.error(err))
    if (response.status === 200){
        const responseArr = await response.json()
        alert(responseArr[0], responseArr[1]);
    }
    getAllUserInfo()
}

const editUser = (id, role) =>{
    pageContent.innerHTML="";
    pageContent.innerHTML=`
    <select id="role" class="form-select mb-2" aria-label="Choose role" onChange="showStaffsForm()">                
                <option value="ROLE_ADMIN" >Admin</option>
                <option value="ROLE_STAFF">Staff</option>
                <option value="ROLE_USER">Borrower</option>
    </select>
    <div id="user-info-form">
                <div class="row">
                  <div class="col-sm-3">
                    <p class="mb-0" value="">First Name</p>
                  </div>
                  <div class="col-sm-6">
                    <input class="text-muted mb-3 form-control " id="firstname"></input>
                  </div>
                  <div class="col-sm-3">
                  </div>
                </div>
                <div class="row">
                  <div class="col-sm-3">
                    <p class="mb-0">Last Name</p>
                  </div>
                  <div class="col-sm-6">
                    <input class="text-muted mb-3 form-control" id="lastname"></input>
                  </div>

                </div>


                <div class="row">
                  <div class="col-sm-3">
                    <p class="mb-0">Email</p>
                  </div>
                  <div class="col-sm-6">
                    <input class="text-muted mb-3 form-control" type="email" id="username"></input>
                  </div>
                </div>

                <div class="row">
                  <div class="col-sm-3">
                    <p class="mb-0">Phone</p>
                  </div>
                  <div class="col-sm-6">
                    <input class="text-muted mb-3 form-control" type="tel" id="phone-number"></input>
                  </div>
                </div>

                <div class="row">
                  <div class="col-sm-3">
                    <p class="mb-0">Password</p>
                  </div>
                  <div class="col-sm-6">
                    <input class="text-muted mb-3 form-control" type="password" id="password" ></input>
                  </div>
                </div>

                <div class="d-none" id="borrower-info-form">

                  <div class="row">
                    <div class="col-sm-3">
                      <p class="mb-0">Address</p>
                    </div>
                    <div class="col-sm-6">
                      <input class="text-muted mb-3 form-control" id="address"></input>
                    </div>
                  </div>

                  <div class="row">
                    <div class="col-sm-3">
                      <p class="mb-0">Credit Score</p>
                    </div>
                    <div class="col-sm-6">
                      <input class="text-muted mb-3 form-control" type="number" id="credit-score"></input>
                    </div>
                    <div class="col-sm-3">
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-sm-3">
                      <p class="mb-0">Loan Amount</p>
                    </div>
                    <div class="col-sm-6">
                      <input class="text-muted mb-3 form-control" type="number" id="loan-amount"></input>
                    </div>
                  </div>

                  <div class="row mb-3">
                    <div class="col-sm-3">
                      <p class="mb-0">Loan Purpose</p>
                    </div>
                    <div class="col-sm-6">
                       <select class="text-muted mb-0 col-sm-12 " name="loan-purpose" id="loan-purpose">
                        <option value="Purchase">Purchase</option>
                        <option value="Refinance">Refinance</option>
                        <option value="Refinance-Cashout">Refinance Cashout</option>
                      </select>
                    </div>
                  </div>

                  <div class="row mb-3">
                    <div class="col-sm-3">
                      <p class="mb-0">Loan term</p>
                    </div>
                    <div class="col-sm-6">
                      <select class="text-muted mb-0 col-sm-12 " name="loan-term" id="loan-term">
                        <option value="30">30 Years</option>
                        <option value="20">20 Years</option>
                        <option value="15">15 Years</option>
                      </select>
                    </div>
                  </div>

                  <div class="row mb-3">
                    <div class="col-sm-3">
                      <p class="mb-0">Loan Type</p>
                    </div>
                    <div class="col-sm-6">
                       <select class="col-sm-12 mb-0 text-muted " name="loantype" id="loan-type">
                        <option value="Conventional">Conventional</option>
                      </select>
                    </div>
                  </div>

                  <div class="row mb-3">
                    <div class="col-sm-3">
                      <p class="mb-0">Occupancy</p>
                    </div>
                    <div class="col-sm-6">
                      <select class="col-sm-12 mb-0 text-muted " name="occupancy" id="occupancy">
                        <option value="owner-occupied">Owner Occupied</option>
                        <option value="Second Home">Second Home</option>
                        <option value="Investment">Investment</option>
                      </select>
                    </div>
                  </div>

                  <div class="row mb-3">
                    <div class="col-sm-3">
                      <p class="mb-0">Property Type</p>
                    </div>
                    <div class="col-sm-6">
                      <select class="col-sm-12 mb-0 text-muted " name="property-type" id="property-type">
                        <option value="Single House">Single House</option>
                        <option value="Townhouse">Townhouse</option>
                        <option value="Condo">Condo</option>
                        <option value="Duplex">Duplex</option>
                        <option value="Triplex">Triplex</option>
                        <option value="Fourplex">Fourplex</option>
                      </select>
                    </div>
                  </div>

                  <div class="row">
                    <div class="col-sm-3">
                      <p class="mb-0">Property Value</p>
                    </div>
                    <div class="col-sm-6">
                      <input class="text-muted mb-3 form-control" type="number" id="property-value"></input>
                    </div>
                  </div>

                </div>
                <button id="saveBtn" type="button" class="btn btn-success col-sm-3" onclick="submitEdit(${id})">Save
                </button>
              </div>
    `

    const  getUserInfo = async() =>{
        const response = await fetch(`${baseUrl}/admin/getuser/${id}`, {
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
        firstName.value=obj.firstname;
        lastName.value=obj.lastname;
        email.value=obj.username;
        phone.value=obj.phonenumber;
        if(role.includes("USER")){
            borrowerInfoForm.classList.remove("d-none");
            address.value=obj.borrowerDto.address;
            creditScore.value=obj.borrowerDto.creditScore;
            loanAmount.value=obj.borrowerDto.loanAmount
            loanPurpose.value=obj.borrowerDto.loanPurpose
            loanTerm.value=obj.borrowerDto.loanTerm
            loanType.value=obj.borrowerDto.loanType
            occupancy.value=obj.borrowerDto.occupancyType
            propertyType.value=obj.borrowerDto.propertyType
            propertyValue.value=obj.borrowerDto.propertyValue
        }

    }

    getUserInfo();

    const roleSelection = document.getElementById("role");
    //declare a function to check the editing user role and calling it at the same time.
    //This function will check user role then select that role from selection options.
    (function() {
        for(let i =0; i< roleSelection.length; i++){
            if( roleSelection[i].value=== "ROLE_" + role){
                roleSelection[i].selected ="selected";
             }
        }
    }());
}
const submitEdit = async (id) => {
    const newRole = document.getElementById("role");
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
    let bodyObj = {
        id: id,
        firstname: firstName.value,
        lastname: lastName.value,
        password: password.value,
        username: email.value,
        phonenumber: phone.value
    }
    if(newRole.value==="ROLE_USER"){
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
    console.log(bodyObj,newRole.value)
    const response =  await fetch(`${baseUrl}/admin/edituser/${newRole.value}`, {
        method: "PUT",
        body: bodyObj,
        headers: headers
    })
        .catch(err => console.error(err))
    password.innerHTML=''
    if (response.status === 200){
        const responseArr = await response.json()
        alert(responseArr[0], responseArr[1]);
    }
}
// //to show form when role admin or staff is chosen

const showStaffsForm = () =>{
    const borrowerInfoForm = document.getElementById("borrower-info-form")
    const role = document.getElementById("role");
    const userInfoForm = document.getElementById("user-info-form")
    userInfoForm.classList.remove("d-none")
    if(role.value==="ROLE_USER"  ){
        borrowerInfoForm.classList.remove("d-none")
    } else{
        borrowerInfoForm.classList.add("d-none")
    }
}


const alert = (message, type) => {
    const wrapper = document.createElement('div')
    wrapper.innerHTML = [
        `<div class="alert alert-${type} alert-dismissible" role="alert">`,
        `   <div>${message}</div>`,
        '   <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>',
        '</div>'
    ].join('')

    alertPlaceholder.append(wrapper)
}

const logOut = async() =>{
    await fetch(`${baseUrl}/logout`, {
        method: "POST",
        headers: headers
    })
        .catch(err => console.error(err))
        .then(window.location.reload())
}
logOutBtn.addEventListener("click", logOut)