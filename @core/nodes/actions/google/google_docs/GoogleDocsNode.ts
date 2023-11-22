// @core/nodes/actions/google/google_docs/GoogleDocsNode
import { NodeBase } from "../../../src/NodeBase";
import { OAuth2Credential } from "../../../src/credentials/OAuth2Credential";
import { INodeParam } from "../../../common/interfaces/INodeParam";

export class GDocsNode extends NodeBase {
  constructor(
    name: string,
    params: { [key: string]: INodeParam },
    credentials: OAuth2Credential
  ) {
    super(name, params, credentials);
  }

  async execute(input: any): Promise<any> {
    const credential = this.getCredential();

    if (!credential) {
      throw new Error("Credential not found");
    }

    if (!(credential instanceof OAuth2Credential)) {
      throw new Error("Credential is not an instance of OAuth2Credential");
    }

    const response = await fetch("https://docs.googleapis.com/v1/documents", {
      method: "POST",
      headers: {
        Authorization: `Bearer ${credential.getAccessTokenValue()}`,
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        /* request body */
      }),
    });

    const data = await response.json();
    return data;
  }
}
