## 📣 숏링크 생성
<br>



### ✅ POST /short-links 
#### 숏링크 생성
- Request <br>
![image](https://user-images.githubusercontent.com/81916648/135050122-fbc676e2-40b3-4c64-aab6-7c3f2f86acc4.png)

- Response <br>
![image](https://user-images.githubusercontent.com/81916648/135050040-e9ba2f70-6f27-48bd-ad56-47ea4b928cc8.png)
<br>

### ✅ GET /r/{short_id} 
#### 리다이렉트
302 redirect
<br><br>

### ✅ GET /short-links?size=3&page=1 
#### 숏링크 목록(페이징)
![image](https://user-images.githubusercontent.com/81916648/135052610-e0176ec0-6aaf-41b4-9f7f-0f6a20dd1d28.png)
<br><br>

### ✅ GET /short-links/{short_id} 
#### 숏링크 확인
![image](https://user-images.githubusercontent.com/81916648/135052968-fcbbff35-11d7-4484-873b-491c055b348a.png)
<br><br>

### ✅ PATCH /short-links/{short_id} 
#### 숏링크의 별명(사용자지정 이름) 설정
- Request <br>
![image](https://user-images.githubusercontent.com/81916648/135058075-e9ba09f6-3099-48ad-8635-85b413f445f8.png)

- Result <br>
![image](https://user-images.githubusercontent.com/81916648/135053832-e9c1f4c6-9f59-4eed-97d7-71e29c4b35a7.png)
<br><br>

### ✅ GET /a/{alias_name} 
#### 별명으로 리다이렉트
302 redirect
<br><br>

### ✅ DELETE /short-links/{short_id} 
#### 숏링크 삭제
![image](https://user-images.githubusercontent.com/81916648/135054102-55a17d64-f67a-4410-9e45-08786a1cb89b.png)<br>
URL has been deleted successfully.
<br><br>
