import { HttpInterceptorFn } from '@angular/common/http';

export const loadingInterceptorInterceptor: HttpInterceptorFn = (req, next) => {
  return next(req);
};
