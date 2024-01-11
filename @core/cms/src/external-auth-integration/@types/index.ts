import { OAuthString } from "../auth-providers/oauth2/@types";
import { BasicAuthString } from "../auth-providers/basic-auth/@types";
import { IOAuth } from "../auth-providers/oauth2/interface/ioauth.interface";
import { IBasicAuth } from "../auth-providers/basic-auth/interface/basic-auth.interface";

export type AuthString = OAuthString | BasicAuthString;
export type AuthService = IOAuth | IBasicAuth;
