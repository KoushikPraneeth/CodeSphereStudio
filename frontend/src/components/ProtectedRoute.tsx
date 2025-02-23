import { Navigate, Outlet } from 'react-router-dom'
import { useAuth } from '@/lib/auth'

export const ProtectedRoute = () => {
  const isAuthenticated = useAuth(state => state.isAuthenticated)

  if (!isAuthenticated()) {
    return <Navigate to="/login" replace />
  }

  return <Outlet />
}