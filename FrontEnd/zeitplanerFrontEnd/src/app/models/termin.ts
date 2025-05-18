export interface TerminDto {
  id?: number;
  beginn: string;              // ISO-String: "2025-06-01T09:00:00"
  ende: string;
  titel: string;
  kategorieId: number;
  wiederholungsRegel?: string;
}
