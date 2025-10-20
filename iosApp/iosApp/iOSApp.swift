import SwiftUI
import shared

@main
struct iOSApp: App {

    init() {
      //用于初始化多模块的页面服务
      PageInitializer.shared.initializePages()
    }

	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}