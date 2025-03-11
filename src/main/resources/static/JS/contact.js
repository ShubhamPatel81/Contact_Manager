console.log("contact js page");
const baseURL = "http://localhost:8081";
// import { Modal } from 'flowbite';
const viewContactModel = document.getElementById("view_contact_madal");
const options = {
  placement: 'bottom-right',
  backdrop: 'dynamic',
  backdropClasses:
    'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
  closable: true,
  onHide: () => {
    console.log('modal is hidden');
  },
  onShow: () => {
    console.log('modal is shown');
  },
  onToggle: () => {
    console.log('modal has been toggled');
  },
};
// instance options object
const instanceOptions = {
  id: 'view_contact_madal',
  override: true
};

const contactModal = new Modal(viewContactModel, options, instanceOptions);

function openContactModal() {
  contactModal.show();
}
function closeContactModal() {
  contactModal.hide();
}


async function loadContactData(id) {
  console.log(`Fetching contact with ID: ${id}`);
  try {
    const data = await (await fetch(`${baseURL}/api/contacts/${id}`))
      .json();
    console.log("Data fetched successfully");
    console.log(data);
    console.log(data.name);
    console.log(data.email);

    // document.getElementById("contact_image_model").src = data.picture;
    document.getElementById("contact_modal_name").innerHTML = data.name;
    document.getElementById("contact_modal_email").innerHTML = data.email;
    document.getElementById("contact_modal_phone").innerHTML = data.phoneNumber;
    document.getElementById("contact_modal_address").innerHTML = data.address;
    document.getElementById("contact_modal_linkedIn").innerHTML = data.linkedInLink;
    document.getElementById("contact_modal_linkedIn").src = data.linkedInLink;
    document.getElementById("contact_modal_website").innerHTML = data.websiteLink;
    document.getElementById("contact_modal_website").src = data.websiteLink;
    // favourate 
    const contactFavorite = document.querySelector("#contact_modal_favorite");
    if (data.favourate) {
      contactFavorite.innerHTML =
        "<i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i>";
    } else {
      contactFavorite.innerHTML = "Not Favorite Contact";
    }

    // 

    openContactModal();
  }

  catch (e) {
    console.log(e);
  }
}

//delete contact
async function deleteContact(id) {
  Swal.fire({
    title: "Do you want to delete the Contact?",
    icon: "warning",
    showCancelButton: true,
    confirmButtonText: "Delete",
    cancelButtonText: "Cancel",
    customClass: {
      confirmButton: 'custom-confirm-button',
      cancelButton: 'custom-cancel-button'
    },
  }).then((result) => {
    if (result.isConfirmed) {
      const url = `${baseURL}/user/contact/delete/` + id;
      window.location.replace(url);
    }
  });
}

