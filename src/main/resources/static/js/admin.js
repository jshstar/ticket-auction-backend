let zones = [];

function addZone() {
    const zoneName = $('#zoneName').val().trim();
    const seatsCount = $('#zoneSeats').val().trim();

    if (!isValidInput(zoneName, seatsCount)) {
        return;
    }
    // 입력 받은 데이터를 zones 배열에 추가합니다.
    zones.push({zoneName, seatsCount: parseInt(seatsCount, 10)});

    // 입력 받은 데이터를 zones 배열에 추가합니다.
    zones.push({zoneName, seatsCount: parseInt(seatsCount, 10)});

    // 입력된 구역명과 좌석 수로 테이블에 새로운 행을 추가합니다.
    addRowToTable(zoneName, seatsCount);

    // 입력 칸 초기화
    $('#zoneName').val('');
    $('#zoneSeats').val('');
}

function addRowToTable(zoneName, seatsCount) {
    const newRow = $('<tr>').append(
        $('<td>').text(zoneName),
        $('<td>').text(seatsCount),
        $('<td>').append($('<button>').text('제거').addClass('remove-zone'))
    );

    $('#zonesTable').append(newRow);
}

function isValidInput(zoneName, seatsCount) {
    if (!zoneName || !seatsCount || isNaN(parseInt(seatsCount, 10))) {
        alert('유효한 구역명과 좌석 수를 입력해주세요.');
        return false;
    }
    return true;
}

function removeZone(button) {
    // 제거 버튼을 클릭한 행을 찾아서 삭제합니다.
    $(button).closest('tr').remove();
}


function submitPlace() {
    const name = $('#placeName').val().trim();
    const address = $('#address').val().trim();
    let zoneInfos = zones.map(zone => ({
        name: zone.zoneName,
        seatNumber: zone.seatsCount
    }));

    if (!isValidPlaceInput(name, address, zoneInfos)) {
        return;
    }

    const placeRequest = {
        name: name,
        address: address,
        zoneInfos: zoneInfos
    };

    // 토큰을 로컬 스토리지에서 가져옵니다.
    var token = Cookies.get('Authorization');

    $.ajax({
        url: '/api/v1/admin/places',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(placeRequest),
        beforeSend: function (xhr) {
            if (token) {
                xhr.setRequestHeader('Authorization', token);
            }
        },
        success: function (response) {
            handleSuccess(response);
        },
        error: function (xhr, status, error) {
            handleError(xhr.statusText);
        }
    });

    function isValidPlaceInput(name, address, zoneInfos) {
        if (!name || !address || zoneInfos.length === 0) {
            alert('모든 필드를 채워주세요.');
            return false;
        }
        return true;
    }

    function handleSuccess(response) {
        console.log('Success:', response);
        alert('공연장이 성공적으로 추가되었습니다.');

        // 테이블의 데이터만 삭제
        $('#zonesTable tr:not(#inputRow)').remove();
        zones = []; // zones 배열 초기화
    }

    function handleError(error) {
        console.error('Error:', error);
        alert('오류가 발생했습니다. 공연장을 추가하지 못했습니다. 오류: ' + error);
    }
}
