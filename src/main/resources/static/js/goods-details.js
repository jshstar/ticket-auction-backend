$(document).ready(function () {
    var queryParams = getQueryParams();
    if (queryParams["goodsId"]) {
        let id = decode(queryParams["goodsId"]);
        fetchGoodsInfo(id);
        fetchGradesInfo(id);
        fetchScheduleInfo(id);
    } else {
        // goodsId가 없다면 에러 처리
        console.error('No goodsId found');
    }
});

var selectScheduleId = -1;

function fetchGoodsInfo(goodsId) {
    $.ajax({
        url: getUrl() + `/api/v1/goods/${goodsId}`,
        type: 'GET',
        success: function (data) {
            var response = data.data;
            $('#goodsTitle').text(response.title);
            $('#main-image').attr('src', response.s3Urls[0]);

            var imagesList = $('#images-container .images-list');
            imagesList.empty(); // 기존 이미지들 제거

            response.s3Urls.forEach(function (url, index) {
                if (index > 0) {
                    imagesList.append($('<img>').attr('src', url).attr('alt', '상세 이미지 ' + index));
                }
            });

            $('#placeFullName').text(response.placeName);
            $('#placeLocationAddress').text(response.placeAddress);
            $('#goodsDate').text(formatDate(response.startDate) + ' - ' + formatDate(response.endDate));
            $('#goodsTime').text(response.runningTime + '분');
            $('#goodsAge').text(response.ageGrade);

            // 여기에 추가적인 처리를 할 수 있습니다.
        },
        error: function (error) {
            console.error('Error fetching goods info:', error);
        }
    });
}

function fetchGradesInfo(goodsId) {
    $.ajax({
        url: getUrl() + `/api/v1/goods/${goodsId}/grade`,
        type: 'GET',
        success: function (data) {
            var response = data.data; // 가정: 응답이 { data: List<GradeGetResponse> } 형태라고 가정
            response.forEach(function (grade) {
                $('#grades-list').append(
                    $('<div>').append(
                        $('<span>').text(grade.name + ': '),
                        $('<span>').text(grade.normalPrice + '원 ')
                    )
                );
            });
        },
        error: function (error) {
            console.error('Error fetching grades info:', error);
        }
    });
}

function fetchScheduleInfo(goodsId) {
    $.ajax({
        url: getUrl() + `/api/v1/goods/${goodsId}/schedules`,
        type: 'GET',
        success: function (data) {
            var events = data.data.map(function (schedule) {
                return {
                    title: schedule.sequence + '차',
                    start: new Date(schedule.startDateTime),
                    id: schedule.scheduleId
                };
            });
            initCalendar(events);
        },
        error: function (error) {
            console.error('Error fetching schedule info:', error);
        }
    });
}

function initCalendar(events) {
    $('#calendar').fullCalendar({
        defaultView: 'month',
        events: events,
        eventClick: function (calEvent) {
            $('#schedule-details').text('선택하신 회차: ' + calEvent.title);
            selectScheduleId = calEvent.id;
        }
    });
}

$('#book-btn').click(function () {
    if (selectScheduleId !== -1) {
        const queryParams = getQueryParams();
        const goodsId = decode(queryParams["goodsId"]);
        const paramValueMap = {
            goodsId: goodsId,
            scheduleId: selectScheduleId
        };
        redirectToPageWithParameters('/reservation/goods_reserve.html', paramValueMap);
    } else {
        alert('선택한 회차가 없습니다');
    }
});

function showGrades() {
    $('#grades-list').toggle(); // show와 hide를 toggle로 변경
}

function formatDate(dateString) {
    var date = new Date(dateString);
    return date.getFullYear() + '년 ' + (date.getMonth() + 1) + '월 ' + date.getDate() + '일';
}
