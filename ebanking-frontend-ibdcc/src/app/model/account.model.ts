export interface AccountDetails {
  accountId:            string;
  accountOperationDTOS: AccountOperation[];
  balance:              number;
  currentPage:          number;
  totalPages:           number;
  pageSize:             number;
}

export interface AccountOperation {
  id:            number;
  dateOperation: Date;
  amount:        number;
  type:          string;
  description:   string;
}
