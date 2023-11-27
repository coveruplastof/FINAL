// script.js

async function setupCamera() {
    const video = document.getElementById('video');
    const stream = await navigator.mediaDevices.getUserMedia({ 'video': {} });
    video.srcObject = stream;

    return new Promise((resolve) => {
        video.onloadedmetadata = () => {
            resolve(video);
        };
    });
}

async function detectFace() {
    const video = await setupCamera();
    const canvas = faceapi.createCanvasFromMedia(video);
    document.body.append(canvas);
    const displaySize = { width: video.width, height: video.height };
    faceapi.matchDimensions(canvas, displaySize);

    setInterval(async () => {
        const detections = await faceapi.detectAllFaces(video, new faceapi.TinyFaceDetectorOptions()).withFaceLandmarks().withFaceDescriptors();
        const resizedDetections = faceapi.resizeResults(detections, displaySize);
        canvas.getContext('2d').clearRect(0, 0, canvas.width, canvas.height);
        faceapi.draw.drawDetections(canvas, resizedDetections);
        faceapi.draw.drawFaceLandmarks(canvas, resizedDetections);
    }, 100);
}



async function detectFaceAndSubmit() {
    const video = await setupCamera();
    const canvas = faceapi.createCanvasFromMedia(video);
    document.body.append(canvas);
    const displaySize = { width: video.width, height: video.height };
    faceapi.matchDimensions(canvas, displaySize);


    setInterval(async () => {
        const detections = await faceapi.detectAllFaces(video, new faceapi.TinyFaceDetectorOptions()).withFaceLandmarks().withFaceDescriptors();
        const resizedDetections = faceapi.resizeResults(detections, displaySize);
        canvas.getContext('2d').clearRect(0, 0, canvas.width, canvas.height);
        faceapi.draw.drawDetections(canvas, resizedDetections);
        faceapi.draw.drawFaceLandmarks(canvas, resizedDetections);
    }, 100);
}

function analyzeSkinConditions(detections) {
    // 가상의 가중치를 이용하여 피부 상태를 분석하는 로직 (실제로는 더 정교한 분석이 필요합니다)
    
}

detectFaceAndSubmit();

//설문조사 시작
const questions = [
    "얼굴형은 어디에 가깝나요?\na: 긴 얼굴/각진 얼굴/하트형\nb: 계란형/둥근형",
    "옆모습의 높이는 어떤가요?\na: 가파르다\nb: 완만하다",
    "옆모습의 높이는 어떤가요?\na: 가로로 넓고 길다\nb: 세로가 높고 동그랗다",
    "입술의 두께는 어떤가요?\na: 얇다\nb:두껍다",
    "이마의 두께감은 어떤가요?\na:울퉁불퉁하고 살이 없다 \nb:도톰하게 살이 있다.",

    // 다음 질문들 추가
  ];
  
  let currentQuestionIndex = 0;
  let userResponses = []; // 사용자 응답을 저장할 배열
  
  function startSurvey() {
    const questionContainer = document.getElementById("questionContainer");
    const choicesContainer = document.getElementById("choices");
    const nextBtn = document.getElementById("nextQuestionBtn");
  
    function showQuestion() {
      const currentQuestion = questions[currentQuestionIndex];
      questionContainer.textContent = currentQuestion;
  
      // 선택지 생성 및 라디오 버튼 추가
      const choices = ["a ", "b "]; // 추가 선택지가 있을 경우 여기에 계속 추가
      choicesContainer.innerHTML = ""; // 이전 선택지 초기화
  
      choices.forEach((choice, index) => {
        const choiceLabel = document.createElement("label");
        choiceLabel.innerHTML = choice;
        const radioButton = document.createElement("input");
        radioButton.type = "radio";
        radioButton.name = "choice";
        radioButton.value = index;
        choiceLabel.appendChild(radioButton);
        choicesContainer.appendChild(choiceLabel);
      });
    }
  
    function handleNextQuestion() {
        const selectedChoice = document.querySelector('input[name="choice"]:checked');
        if (selectedChoice) {
            userResponses.push(selectedChoice.value);
            currentQuestionIndex++;
            if (currentQuestionIndex < questions.length) {
                showQuestion();
            } else {
                // 설문조사 종료 시에 결과 분석 함수 호출
                endSurvey();
            }
        } else {
            alert("선택지를 선택해주세요.");
        }
    }
  
    nextBtn.addEventListener("click", handleNextQuestion);
    showQuestion();
  }
  
  document.getElementById("submitBtn").addEventListener("click", startSurvey);
  
  
/*붙여넣기*/
/*설문조사 끝난후 로직*/
function analyzeFaceShape(userResponses) {
    // 각 문항별 응답 개수를 저장할 객체
    const responseCounts = {
        "a": 0,
        "b": 0
    };

    // 각 응답의 개수를 세기
    userResponses.forEach(response => {
        responseCounts[response]++;
    });

    // 판단 로직 추가 (예시: a가 4-5개면 직선골격형, 2-3개면 밸런스형, 1개 이하면 곡선 볼륨형)
    if (responseCounts["a"] >= 4) {
        return "직선골격형";
    } else if (responseCounts["a"] >= 2) {
        return "밸런스형";
    } else {
        return "곡선 볼륨형";
    }
}

// video 엘리먼트 가져오기
const videoElement = document.getElementById("video");

// 다음 버튼 엘리먼트 가져오기
const nextBtn = document.getElementById("nextQuestionBtn");

// video 엘리먼트의 너비를 가져와서 다음 버튼의 스타일을 동적으로 조정
function adjustNextButtonSize() {
    const videoWidth = videoElement.offsetWidth; // video 엘리먼트의 너비 가져오기
    const newButtonWidth = videoWidth * 0.1; // 버튼 너비를 video 너비의 10%로 설정 (예시로 10%로 설정함)
    nextBtn.style.width = newButtonWidth + "px"; // 버튼 너비 조정
}

// 페이지 로딩 시에도 실행되도록 설정
window.addEventListener("load", adjustNextButtonSize);

// 창 크기가 변경될 때마다 실행되도록 설정 (예시로 resize 이벤트 사용)
window.addEventListener("resize", adjustNextButtonSize);



/*설문조사 끝난후 로직*/


// 설문이 완료된 후에 호출되는 함수
function endSurvey() {
    // 설문 결과 분석
    const faceShape = analyzeFaceShape(userResponses);
    // 결과 출력 또는 다른 처리 로직을 여기에 추가
    questionContainer.textContent = `당신의 얼굴 골격 형태는 ${faceShape} 입니다.`;
    choicesContainer.innerHTML = ""; // 선택지 영역 초기화
    nextBtn.style.display = "none"; // "다음" 버튼 숨기기

    // 여기에 추가적인 로직을 넣을 수 있습니다.
    // 예를 들어, 결과를 서버로 보내거나 다른 동작을 수행할 수 있습니다.
}

nextBtn.addEventListener("click", handleNextQuestion);
