import { useState, useEffect } from 'react'
import { useEditorStore } from '@/lib/editor-store'
import { Button } from './ui/button'
import { Input } from './ui/input'
import { 
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from './ui/dialog'
import { 
  File, 
  FolderPlus, 
  Save, 
  Trash2,
  Code,
  FileText
} from 'lucide-react'

interface ProjectFile {
  id: number
  name: string
  content: string
  language: string
  lastModifiedAt: string
}

export function FileExplorer() {
  const [files, setFiles] = useState<ProjectFile[]>([])
  const [newFileName, setNewFileName] = useState('')
  const { setCode, setLanguage } = useEditorStore()

  const fetchFiles = async () => {
    const response = await fetch('http://localhost:8080/api/files', {
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
    })
    const data = await response.json()
    setFiles(data)
  }

  useEffect(() => {
    fetchFiles()
  }, [])

  const createFile = async () => {
    if (!newFileName) return

    await fetch('http://localhost:8080/api/files', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      },
      body: JSON.stringify({
        name: newFileName,
        content: '',
        language: 'javascript'
      })
    })

    setNewFileName('')
    fetchFiles()
  }

  const openFile = async (fileId: number) => {
    const response = await fetch(`http://localhost:8080/api/files/${fileId}`, {
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
    })
    const file = await response.json()
    setCode(file.content)
    setLanguage(file.language)
  }

  const deleteFile = async (fileId: number) => {
    await fetch(`http://localhost:8080/api/files/${fileId}`, {
      method: 'DELETE',
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
    })
    fetchFiles()
  }

  return (
    <div className="w-64 border-r h-full p-4">
      <div className="flex items-center justify-between mb-4">
        <h2 className="font-semibold">Files</h2>
        <Dialog>
          <DialogTrigger asChild>
            <Button size="icon" variant="ghost">
              <FolderPlus className="h-4 w-4" />
            </Button>
          </DialogTrigger>
          <DialogContent>
            <DialogHeader>
              <DialogTitle>Create New File</DialogTitle>
            </DialogHeader>
            <div className="flex gap-2">
              <Input
                value={newFileName}
                onChange={(e) => setNewFileName(e.target.value)}
                placeholder="File name"
              />
              <Button onClick={createFile}>
                <Save className="h-4 w-4" />
              </Button>
            </div>
          </DialogContent>
        </Dialog>
      </div>
      <div className="space-y-2">
        {files.map((file) => (
          <div
            key={file.id}
            className="flex items-center justify-between p-2 hover:bg-muted rounded-md"
          >
            <button
              className="flex items-center gap-2 flex-1"
              onClick={() => openFile(file.id)}
            >
              <File className="h-4 w-4" />
              <span className="text-sm truncate">{file.name}</span>
            </button>
            <Button
              size="icon"
              variant="ghost"
              onClick={() => deleteFile(file.id)}
            >
              <Trash2 className="h-4 w-4" />
            </Button>
          </div>
        ))}
      </div>
    </div>
  )
}