import { FileExplorer } from './FileExplorer'
import { CodeEditor } from './CodeEditor'
import { ChatPanel } from './ChatPanel'

export function Layout() {
  return (
    <div className="flex h-screen">
      <FileExplorer />
      <div className="flex-1 flex">
        <div className="flex-1">
          <CodeEditor />
        </div>
        <ChatPanel className="w-80 border-l" />
      </div>
    </div>
  )
}