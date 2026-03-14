# cmp (compose multi platform - 채팅)

**프로젝트 구조**

- `/composeApp` — Compose Multiplatform 앱들 간에 공유되는 코드. MVVM 패턴 적용
  - `commonMain`: 모든 플랫폼에 공통으로 적용되는 코드
  - 그 외 폴더들은 특정 플랫폼 전용 코드.

- `/iosApp` — iOS 앱의 진입점(entry point).

- `/server` — Ktor 기반 서버 애플리케이션 코드.

- `/shared` — 모든 타겟 간에 공유되는 코드.
