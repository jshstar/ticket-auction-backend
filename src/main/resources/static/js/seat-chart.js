maps = {
    '': {
        map: [
            'aaaaa', // a는 좌석을 의미합니다.
            'aaaaa',
            '_____', // _는 공간(통로 등)을 의미합니다.
            'bbbbb',
            'bbbbb'
        ],
        seats: {
            a: {
                price: 99.99,
                classes: 'front-seat', // 추가 CSS 클래스
                category: 'First Class'
            },
            b: {
                price: 49.99,
                classes: 'economy-seat',
                category: 'Economy Class'
            }
        },
        naming: {
            top: false, // 좌석 위에 행 번호를 표시하지 않음
            getLabel: function (character, row, column) {
                return column; // 좌석의 라벨을 열 번호로 설정
            }
        },
        legend: {
            node: $('#legend'),
            items: [
                ['a', 'available', 'First Class'],
                ['b', 'available', 'Economy Class'],
                ['a', 'unavailable', 'Already Booked']
            ]
        },
        click: function () {
            if (this.status() == 'available') {
                // 선택 가능한 좌석을 클릭했을 때의 액션
                // 예를 들어, 선택한 좌석의 정보를 쇼핑 카트에 추가하는 등의 로직을 실행할 수 있습니다.
                return 'selected'; // 좌석 상태를 '선택됨'으로 변경
            } else if (this.status() == 'selected') {
                // 이미 선택된 좌석을 다시 클릭했을 때의 액션
                // 예를 들어, 쇼핑 카트에서 좌석 정보를 제거하는 등의 로직을 실행할 수 있습니다.
                return 'available'; // 좌석 상태를 '가능'으로 변경
            } else if (this.status() == 'unavailable') {
                // 판매 불가능한 좌석을 클릭했을 때
                return 'unavailable'; // 좌석 상태 유지
            } else {
                return this.style(); // 그 외의 경우, 기본 동작 수행
            }
        }
    }
}
