## 📍파일 관리
  1. ```
     git clone {현재 레포지터리 주소}
     ```
  2. ```
     git pull origin main
     ```
  3. ```
     git checkout -b {브랜치 이름(폴더 이름과 동일한 이름 권장)}
     ```
     <br>
     <br>

  - 그 후 사용
  1. ```
     git status //본인 이름으로 된 브랜치명이 뜨는지 반드시 확인할 것.
     ```
  2. 자유롭게 내용 추가
  3. ```
     git add .
     git commit -m "커밋 컨벤션을 지킨 내용으로 작성"
     git push origin {본인 브랜치}
     ```
  4. merge 진행<br>
     - 방법은 다음 링크 참조 : [Merge 진행 방법!](https://2l24algochallenge.notion.site/Merge-4f0f27f65df34f898e40a10a89041f14?pvs=4)
      
      
#### :ocean:Pull Request convention
- PR 제목 ```[MOGURI-1] add : member CRUD 구현```

- <b>Add a description</b> : 코드 설명 or 구현 중 헷갈리던 것 or 변수명 고민 등을 남겨야 다른 팀원들의 코드 리뷰를 통해 해결할 수 있습니다!


#### :ocean:Commit convention
```
ex) git commit -m"[MOGURI-1] add : member CRUD 구현"
ex) git commit -m"[MOGURI-2] add : accountBook CRUD 구현"

수정하는 경우
git commit -m"[MOGURI-1] fix : member CRUD service update 부분 수정" 
```

#### :ocean:commit message convention
- feat: 새로운 기능 추가
- fix: 버그 수정
- docs: 문서
- style: 포맷팅, 누락된 세미콜론 등
- refactor: 코드 리팩토링
- test: 테스트 관련
- chore: 기타 수정
- build: 빌드 시스템 또는 외부 의존성에 영향을 주는 변경
- remove: 파일을 삭제
