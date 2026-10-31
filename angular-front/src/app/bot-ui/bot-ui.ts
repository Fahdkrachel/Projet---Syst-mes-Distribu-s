import {
  HttpClient,
  HttpDownloadProgressEvent,
  HttpEventType,
  HttpParams,
  HttpResponse,
} from '@angular/common/http';
import { AsyncPipe } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MarkdownModule } from 'ngx-markdown';
import { Observable, filter, map } from 'rxjs';
import { LoadingService } from '../services/loading';

@Component({
  selector: 'app-bot-ui',
  imports: [FormsModule, AsyncPipe, MarkdownModule],
  templateUrl: './bot-ui.html',
  styleUrl: './bot-ui.css',
})
export class BotUi {
  query = '';
  response$!: Observable<string>;

  private http = inject(HttpClient);
  public loadingService = inject(LoadingService);


  private conversationId = crypto.randomUUID();
  private baseUrl = 'http://localhost:9999/EBANK-BOT';

  private params(): HttpParams {
    return new HttpParams()
      .set('query', this.query)
      .set('conversationId', this.conversationId);
  }

  askAgent() {
    if (!this.query.trim()) return;
    this.response$ = this.http.get(`${this.baseUrl}/chat`, {
      params: this.params(),
      responseType: 'text',
    });
  }

  askAgentStream() {
    if (!this.query.trim()) return;
    this.response$ = this.http
      .get(`${this.baseUrl}/chatStream`, {
        params: this.params(),
        responseType: 'text',
        observe: 'events',
        reportProgress: true,
      })
      .pipe(
        filter(
          (e) => e.type === HttpEventType.DownloadProgress || e.type === HttpEventType.Response
        ),
        map((e) =>
          e.type === HttpEventType.DownloadProgress
            ? ((e as HttpDownloadProgressEvent).partialText ?? '')
            : ((e as HttpResponse<string>).body ?? '')
        )
      );
  }
}
