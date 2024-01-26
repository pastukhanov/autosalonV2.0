
function editSelectedObject(url) {
    var selectedObjId = getSelectedObjectId();
    console.log(selectedObjId);
    if (selectedObjId) {
    window.location.href = 'add-'+url +'?id=' + selectedObjId;
    }
}

function deleteSelectedObjects(url) {
    var selectedIds = getSelectedObjectIds();
    console.log(selectedIds);
    if (selectedIds.length === 0) {
        return;
    }
    var completedRequests = 0;

    selectedIds.forEach(function(objId) {
        if (objId) {
            var xhr = new XMLHttpRequest();
            xhr.open("POST", url, true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            xhr.onreadystatechange = function() {
                if (this.readyState == 4) {
                    completedRequests++;
                    if (this.status == 200) {
                        console.log(objId + ' - deleted successful');
                    }
                    if (completedRequests === selectedIds.length) {
                        window.location.reload();
                    }
                }
            };
            var data = "action=delete&id=" + encodeURIComponent(objId);
            xhr.send(data);
        }
    });
}

function getSelectedObjectId() {
    var checkboxes = document.querySelectorAll('input[type="checkbox"]:checked');
    for(var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].checked) {
            return checkboxes[i].getAttribute('data-id');
        }
    }
    return null;
}

function toggleObjSelection(rowElement) {
    var checkbox = rowElement.querySelector('input[type="checkbox"]');
    checkbox.checked = !checkbox.checked;
}

function getSelectedObjectIds() {
    var checkboxes = document.querySelectorAll('input[type="checkbox"]:checked');
    var carIds = [];
    checkboxes.forEach(function(checkbox) {
        carIds.push(checkbox.getAttribute('data-id'));
    });
    return carIds;
}

function toggleAllObjectCheckboxes(masterCheckbox) {
    var isChecked = masterCheckbox.checked;

    var table = masterCheckbox.closest('table');
    var checkboxes = table.querySelectorAll('input[type="checkbox"]');

    checkboxes.forEach(function(checkbox) {
        checkbox.checked = isChecked;
    });
}