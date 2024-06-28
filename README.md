# Spring AI Basic

<br>
<br>

## 개발환경 구성

<br>

- OS: Windows
- Platform: JDK(openjdk) 21+ 권장 / Docker Desktop 또는 WSL 내 Docker Runtime 환경 구성 권장
- IDE: IntelliJ IDEA Ultimate 권장

<br>

## Spring Boot App 실행

<br>

### IDE Java 실행환경에 Open AI의 API Key를 환경변수로 설정해줘야 한다.

![](media/SpringAI_runtime_01.png)


<br>

### Spring AI RAG 과정 진행 시 참고 명령어

<br>

  - Chroma 기동(App 구동 전)

```bash
docker run -it --rm --name chroma -p 8000:8000 ghcr.io/chroma-core/chroma:0.4.15
```

<br>

---

<br>

  - Milvus 기동(App 구동 전)

```bash
docker compose up
```

<br>
<br>