import { BasicAuthString } from "~/external-auth-integration/auth-providers/basic-auth/@types";

export class BasicAuthVerifyDto {
  providerKey: BasicAuthString;
}
