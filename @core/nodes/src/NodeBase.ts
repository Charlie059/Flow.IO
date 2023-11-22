/* eslint-disable @typescript-eslint/no-explicit-any */
// @core/nodes/src/NodeBase.ts
import { ICredential } from "../common/interfaces/ICredential";
import { INodeParam } from "../common/interfaces/INodeParam";
import { uuid } from "uuidv4";

export abstract class NodeBase {
  protected params: { [key: string]: INodeParam };
  protected name: string;
  protected id: string;
  protected credential?: ICredential;

  constructor(
    name: string,
    params: { [key: string]: INodeParam },
    credentials?: ICredential
  ) {
    this.name = name;
    this.params = params;
    this.credential = credentials;
    this.id = this.generateId();
  }

  /**
   * Generate a uuid for the node
   */
  private generateId(): string {
    return uuid();
  }

  abstract execute(input: any): Promise<any>;

  getParams(): { [key: string]: INodeParam } {
    return this.params;
  }

  setParams(newParams: { [key: string]: INodeParam }): void {
    this.params = { ...this.params, ...newParams };
  }

  getName(): string {
    return this.name;
  }

  getId(): string {
    return this.id;
  }

  getCredential(): ICredential | undefined {
    return this.credential;
  }
}
