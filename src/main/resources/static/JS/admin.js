
console.log(" Admin User");

document.getElementById("image_file_input").addEventListener("change", function (event) {
  const file = event.target.files[0];
  if (file) {
    const reader = new FileReader();
    reader.onload = function (e) {
      document.getElementById("upload_image_preview").src = e.target.result;
    };
    reader.readAsDataURL(file);
  }
});
 