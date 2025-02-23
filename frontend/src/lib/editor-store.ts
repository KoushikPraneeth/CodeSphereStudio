import { create } from 'zustand'

interface EditorState {
  code: string
  language: string
  theme: 'vs-dark' | 'light'
  setCode: (code: string) => void
  setLanguage: (language: string) => void
  setTheme: (theme: 'vs-dark' | 'light') => void
}

export const useEditorStore = create<EditorState>((set) => ({
  code: '',
  language: 'javascript',
  theme: 'vs-dark',
  setCode: (code) => set({ code }),
  setLanguage: (language) => set({ language }),
  setTheme: (theme) => set({ theme }),
}))