import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    init() {
            DIHelperKt.doInitKoin()
    }
	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}